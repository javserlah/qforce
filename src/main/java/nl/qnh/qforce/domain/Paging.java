package nl.qnh.qforce.domain;

/**
 * Created by Javi on 21/11/2017.
 * Model for retrieving the original structure from the third party
 */
public class Paging  {

    int count;

    String next;

    String previous;

    PersonModel[] results;

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

    public PersonModel[] getResults() {
        return results;
    }

    public void setResults(PersonModel[] results) {
        this.results = results;
    }
}
