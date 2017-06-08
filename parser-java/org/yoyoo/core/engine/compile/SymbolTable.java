package org.yoyoo.core.engine.compile;

import java.util.LinkedHashMap;
import java.util.Map;

import org.yoyoo.core.engine.parser.SimpleNode;

public class SymbolTable<V> {
	private Map<String, V> symbols;

	public SymbolTable() {
		super();
		this.symbols = new LinkedHashMap<String, V>();
	}

	public SymbolTable(SymbolTable<V> table) {
		super();
		this.symbols = new LinkedHashMap<String, V>();
		if (table != null && table.symbols != null)
			this.symbols.putAll(table.symbols);
	}

	public void putSymbol(String key, V v, SimpleNode node, CompilationUnit unit)
			throws CompileException {
		if (symbols.containsKey(key)) {
			throw new CompileException.DuplicateDefined(key, node, unit);
		} else {
			symbols.put(key, v);
		}
	}
	
	public void forceputSymbol(String key, V v, SimpleNode node, CompilationUnit unit) {
		symbols.put(key, v);		
	}
	
	public void forceremoveSymbol(String key, SimpleNode node, CompilationUnit unit) {
		symbols.remove(key);		
	}

	public V getSymbol(String key) {
		if (!symbols.containsKey(key)) {
			return null;
		} else {
			return symbols.get(key);
		}
	}

	public boolean contains(String key) {
		return symbols.containsKey(key);
	}

	public Map<String, V> getSymbols() {
		return symbols;
	}
	
	public boolean isEmpty() {
		return this.symbols==null || this.symbols.isEmpty();
	}
	
	public int size() {
		return this.symbols.size();
	}

}
