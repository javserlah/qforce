package nl.qnh.qforce.domain;

import java.util.List;
import java.util.Optional;

/**
 * Created by Javi on 21/11/2017.
 * Model for retrieving the original structure from the third party
 */
public class Paging  {

    private int count;

    private String next;

    private String previous;

    private List<PersonModel> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PersonModel> getResults() {
        return results;
    }

    public void setResults(List<PersonModel> results) {
        this.results = results;
    }
}
