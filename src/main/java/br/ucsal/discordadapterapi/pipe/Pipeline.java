package br.ucsal.discordadapterapi.pipe;

import java.util.ArrayList;
import java.util.List;

import br.ucsal.discordadapterapi.filter.Filter;

public class Pipeline<T> {
	
	private List<Filter<T>> filters = new ArrayList<>();
	
	public Pipeline(List<Filter<T>> filters) {
        this.filters = filters;
    }

    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }

    public T process(T input) {
    	T output = input;
        for (Filter<T> filter : filters) {
            output = filter.process(input);
        }
        return output;
    }

}
