package guestbook;

import java.lang.*;
import java.util.HashMap;

public class SessionValueContainers<T> {
	
	// セッション毎に格納するためのKey-Value
	private HashMap<String,T> containers;
	
	public SessionValueContainers()
	{
		this.containers = new HashMap<String,T>();
	}
	
	// containerの存在チェック
	public synchronized boolean Exists(String sessionID)
	{
		return this.containers.get(sessionID) != null;
	}

	// containerオブジェクトの取得
	public synchronized T getContainer(String sessionID)
	{
		return this.containers.get(sessionID);
	}
	
	// containerオブジェクトの追加
	public synchronized void addContainer(String sessionID, T container)
	{
		// 既に登録済みのsessionの場合は例外をthrowする
		if(Exists(sessionID))
		{
			throw new IllegalArgumentException();
		}
		
		this.containers.put(sessionID, container);
	}
	
	// containerオブジェクトの削除
	public synchronized T removeContainer(String sessionID)
	{
		return this.containers.remove(sessionID);
	}
}
