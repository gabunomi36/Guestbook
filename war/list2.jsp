<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="guestbook.*" %>
<%@ page import="com.laughstyle.gae.calendar.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GData API Demo</title>
<jsp:include  page="/header.jsp" />

<script type="text/javascript" > 

var dsOption= {
	    fields :[
	        ,{name : 'title'  }
	        ,{name : 'content'  }
	        ,{name : 'where'  }
	        ,{name : 'startDate',type:'date' }
	        ,{name : 'startTime',type:'date' }
	        ,{name : 'endDate',type:'date' }
	        ,{name : 'endTime',type:'date' }
	        ,{name : 'ext'  }
	        ,{name : 'url'  }
	        ,{name : 'etag'  }
	    ],

	    recordType : 'object'  //JSON=object , Array=array
	};

var colsOption = [  
     {id: 'title' , header: "タイトル" , width :150 ,editor:{type:"text"}}
       ,{id: 'content' , header: "コンテンツ" , width :335,editor:{type:"text"} }
       ,{id: 'where' , header: "場所" , width :150,editor:{type:"text"}}
       ,{id: 'startDate' , header: "開始日" , width :80,editor:{type:"date"}}
       ,{id: 'startTime' , header: "時刻" , width :40,editor:{type:"text"}}
       ,{id: 'endDate' , header: "終了日" , width :80,editor:{type:"date"}}
       ,{id: 'endTime' , header: "時刻" , width :40,editor:{type:"text"}}
       ,{id: 'ext' , header: "拡張" , width :50, hidden:true}
       ,{id: 'url' , header: "url" , width :50, hidden:true}
       ,{id: 'etag' , header: "etag" , width :50, hidden:true}
       ];

var gridOption={
	id : "grid",
	container : 'grid1_container',
	dataset : dsOption,
	replaceContainer : true,
	columns : colsOption,
	width: "900",  
	height: "250",  
	pageSize:30,
	pageSizeList : [5,10,15,20,25,30],
	allowCustomSkin : true,
	skin:"mac",
	resizable : true,
	remotePaging : false,
	remoteSort : false,     	
	remoteFilter : false,

	beforeSave:function(requestParameter){
		if(confirm('編集結果をGoogle Calendarに反映します'))
			return true;
		else
			return false;
	},
	saveFailure:function( respD, respD, exception){
		$.jGrowl('更新処理中にエラーが発生しました');
	},

	beforeEdit:function(  value,  record,  col,  grid){
		if(record["url"] == null)
		{
			$.jGrowl('このカレンダーを編集する権限がありません');
			return false;
		}
		return true;
	}
};

var mygrid1=new Sigma.Grid(gridOption);
Sigma.Utils.onLoad( function(){
	mygrid1.render();
} );
</script> 

<script type="text/javascript">
function IsDate(datestr) { 
	if(!datestr.match(/^\d{4}\-\d{1,2}\-\d{1,2}$/)){ 
		return false; 
	}

	var ymd = datestr.split("-");
    var year  = ymd[0] - 0;
    var month = ymd[1] - 1;
    var day   = ymd[2] - 0;

	if(month >= 0 && month <= 11 && day >= 1 && day <= 31)
	{ 
		var date = new Date(year, month, day); 
		if(isNaN(date))
		{
			return false; 
		}

		return (date.getFullYear() == year &&
				date.getMonth() == month &&
				date.getDate() == day);
	}
	return false;
} 

function reload()
{
	if(!IsDate($("#startDay").val()))
	{
		$.jGrowl('開始日付が未入力か正しくありません');
		return;
	}

	if(!IsDate($("#endDay").val()))
	{
		$.jGrowl('終了日付が未入力か正しくありません');
		return;
	}

	var grid=Sigma.$grid('grid');

 	if(grid == null)
		alert('grid not found');
	else
	{
		grid.loadURL = "/search?token=" + $("#token").val() + "&startday=" + $("#startDay").val() + "&endday=" + $("#endDay").val()  + "&calendar=" + $("#cals").val();;
		grid.saveURL = "/writer?actionMethod=save&token=" + $("#token").val() + "&calendar=" + $("#cals").val();
	   	grid.reload(true,true);
	}
}

</script>
</head>
<body>
	<div id="main" style="width: 900px;">
		<div style= "height:50px;">
		</div>
		<div id="header">
			<Label>From:</Label><input type="text" id="startDay" name="startDay"  size="11" />
			<Label>To:</Label><input type="text" id="endDay" name="endDay"  size="11" />
			<br/>
			<Label>Calendar:</Label>
			<select id="cals">
				<option value="default">default</option>
				<%
					String token = (String)request.getAttribute("token");
					CalendarReader reader = new CalendarReader(token);
					List<String[]> names = reader.getCalendarNames();
					
					for(String[] name: names)
					{
				%>
						<option value=<%=name[1]%>><%=name[0] %></option>
				<%
					}
				%>
			</select>
			<input type="button" id="searchButton" value="検索"/><br/>
			<input type="hidden"id="token" name="token" value=<%=request.getAttribute("token") %>></input>
		</div>
		<div id="contents">
			<div id="grid1_container"style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;width:100%;">
			</div>
		</div>
	
		<script type="text/javascript">
			$(function(){
				$("#startDay,#endDay").daterangepicker({dateFormat:"yy-mm-dd"});
				$("#searchButton").button();
				$("#searchButton").click(function(){reload();});
			});
	
		</script>
		<div>
			<div style="width: 900px;" align="right">
				<br/><img src="http://code.google.com/appengine/images/appengine-silver-120x30.gif" alt="Powered by Google App Engine" />
			</div>
		</div>
	</div>
</body>
</html>