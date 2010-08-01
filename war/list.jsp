<%@ page language="java" contentType="text/html; charset=Shift_JIS"
    pageEncoding="Shift_JIS"%>
<%@ page import="java.util.*" %>
<%@ page import="guestbook.*" %>
<%@ page import="com.laughstyle.gae.calendar.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Shift_JIS">
<title>Insert title here</title>
<link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/themes/start/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load("jquery","1");
	google.load("jqueryui","1.8");
</script>

<script type="text/javascript" src="/scripts/gt_grid_all.js"></script>
<script type="text/javascript" src="/scripts/gt_msg_en.js"></script>
<link rel="stylesheet" type="text/css" href="/stylesheets/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/skin/china/skinstyle.css" />

<script type="text/javascript" > 
<!-- All the scripts will go here  -->
var data1 = [
{serialNo:"010-0",country:"MA",employee:"Jerry",customer:"Keith",order2005:50,order2006:57,order2007:80,order2008:46,lastDate:"2008-10-02"},
{serialNo:"010-1",country:"SP",employee:"Charles",customer:"Marks",order2005:79,order2006:37,order2007:40,order2008:90,lastDate:"2008-04-24"},
{serialNo:"010-2",country:"SP",employee:"Vincent",customer:"Harrison",order2005:91,order2006:75,order2007:31,order2008:40,lastDate:"2008-02-17"},
{serialNo:"020-3",country:"RA",employee:"Edward",customer:"Sidney",order2005:61,order2006:31,order2007:80,order2008:47,lastDate:"2008-10-16"},
{serialNo:"022-1",country:"JA",employee:"Taro",customer:"Japan",order2005:161,order2006:131,order2007:180,order2008:147,lastDate:"2010-02-13"},
{serialNo:"030-1",country:"日本",employee:"一日一善",customer:"追加野郎",order2005:100,order2006:200,order2007:300,order2008:400,lastDate:"2010-02-13"}
];

var dsOption1= {
        fields :[
                {name : "serialNo"},
                {name : "country"},
                {name : "customer"  },
                {name : "employee"},
                {name : 'order2005' ,type: 'float' },
                {name : 'order2006' ,type: 'float' },
                {name : 'order2007' ,type: 'float' },
                {name : 'order2008' ,type: 'float' },
                {name : 'lastDate' ,type:'date'  }
        ],
        recordType : 'object',
        data: data1
};

var colsOption1 = [
     {id: 'serialNo' , header: "注文番号" , width :60 },
     {id: 'employee' , header: "従業員" , width :80  },
           {id: 'country' , header: "国" , width :70  },
           {id: 'customer' , header: "顧客" , width :80  },
           {id: 'order2005' , header: "2005" , width :60},
           {id: 'order2006' , header: "2006" , width :60},
           {id: 'order2007' , header: "2007" , width :60},
           {id: 'order2008' , header: "2008" , width :60},
           {id: 'lastDate' , header: "発送日" , width :100}
       
];

var gridOption1={
        id : "grid2",
        container : 'grid1_container',
        dataset : dsOption1 ,
        columns : colsOption1
};

var mygrid1=new Sigma.Grid(gridOption1);
Sigma.Util.onLoad( Sigma.Grid.render(mygrid1) );
 
</script> 
</head>
<body>
	<div id="gridbox">
	</div>
	
	<div id="grid1_container">
	</div>
</body>
</html>