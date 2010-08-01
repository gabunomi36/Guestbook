package guestbook;

import java.lang.*;
import java.util.HashMap;

public class SessionValueContainers<T> {
	
	// �Z�b�V�������Ɋi�[���邽�߂�Key-Value
	private HashMap<String,T> containers;
	
	public SessionValueContainers()
	{
		this.containers = new HashMap<String,T>();
	}
	
	// container�̑��݃`�F�b�N
	public synchronized boolean Exists(String sessionID)
	{
		return this.containers.get(sessionID) != null;
	}

	// container�I�u�W�F�N�g�̎擾
	public synchronized T getContainer(String sessionID)
	{
		return this.containers.get(sessionID);
	}
	
	// container�I�u�W�F�N�g�̒ǉ�
	public synchronized void addContainer(String sessionID, T container)
	{
		// ���ɓo�^�ς݂�session�̏ꍇ�͗�O��throw����
		if(Exists(sessionID))
		{
			throw new IllegalArgumentException();
		}
		
		this.containers.put(sessionID, container);
	}
	
	// container�I�u�W�F�N�g�̍폜
	public synchronized T removeContainer(String sessionID)
	{
		return this.containers.remove(sessionID);
	}
}
