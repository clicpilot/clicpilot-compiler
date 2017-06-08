package org.yoyoo.core.engine.compile;

public class VirtualFile {

	private String name;

	public VirtualFile(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean equals(Object arg0) {
		return arg0 instanceof VirtualFile && ((VirtualFile)arg0).name.equals(name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public String getPath() {
		
		return name;
	}

	public boolean isDirectory() {
		return name.endsWith("/");
	}

	public boolean exists() {
		return VirtualFileSystem.VirtualFileSystemMap.containsKey(name);
	}

	public String getParent() {
		return name.substring(0, name.lastIndexOf("/", name.length()-1));
	}
	
	
	
}
