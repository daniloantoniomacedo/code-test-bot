package br.ucsal.discordadapterapi.filter;

public interface Filter<T> {
	
	T process(T input);

}
