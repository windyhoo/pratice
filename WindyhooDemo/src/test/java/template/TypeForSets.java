package template;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

class SetType {
	int i;
	int j=10;
	
	public SetType(int n) {
		i=n;
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + i;
//		return result;
//	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetType other = (SetType) obj;
		if (i != other.i)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SetType [i=" + i + "]";
	}
	
	
}

class HashType extends SetType {
	public HashType(int n) {
		super(n);
	}
	
	public int hashCode() {
		return i;
	}
}

class TreeType extends SetType implements Comparable<TreeType>{
	public TreeType(int n) {
		super(n);
	}

	public int compareTo(TreeType o) {
		return (o.i<i?-1:(o.i==i?0:1));
	}
}

public class TypeForSets {
	
	static <T> Set<T> fill(Set<T> set,Class<T> type) {
		for(int i=0;i<10;++i) {
			try {
				set.add(type.getConstructor(int.class).newInstance(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return set;
	}
	
	static <T> void test(Set<T> set,Class<T> type) {
		fill(set,type);
		fill(set,type);
		fill(set,type);
		System.out.println(set);
	}

	public static void main(String[] args) {
		test(new HashSet<HashType>(),HashType.class);
	}
}

