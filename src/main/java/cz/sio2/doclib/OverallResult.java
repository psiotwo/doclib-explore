package cz.sio2.doclib;

import java.util.HashMap;
import java.util.Map;

public class OverallResult {

    private Map<String, Integer> numberOfPages;
    private Map<String, Integer> numberOfWords;

    public OverallResult() {
        numberOfPages = new HashMap<String, Integer>();
        numberOfWords = new HashMap<String, Integer>();
    }

    private void add(final String ext, final Integer number, final Map<String,Integer> map) {
        if (!map.containsKey(ext)) {
            map.put(ext,0);
        }
        int count = map.get(ext);
        map.put(ext, count+number);
    }

    public void addNumberOfPages(final String ext, final Integer number) {
        add(ext,number,numberOfPages);
    }

    public void addNumberOfWords(final String ext, final Integer number) {
        add(ext,number,numberOfWords);
    }

    public Map<String, Integer> getNumberOfPages() {
        return numberOfPages;
    }

    public Map<String, Integer> getNumberOfWords() {
        return numberOfWords;
    }

    public Integer getTotalNumberOfPages() {
        int i = 0;
        for (String key:numberOfPages.keySet()) {
            i += numberOfPages.get(key);
        }
        return i;
    }

    public Integer getTotalNumberOfWords() {
        int i = 0;
        for (String key:numberOfWords.keySet()) {
            i += numberOfWords.get(key);
        }
        return i;
    }

    public void addExplorationResult(final String ext, final ExplorationResult r) {
        addNumberOfPages(ext, r.getNumberOfPages());
        addNumberOfWords(ext, r.getNumberOfWords());
    }

    @Override
    public String toString() {
        return "OverallResult{" +
                "numberOfPages=" + numberOfPages +
                ", numberOfWords=" + numberOfWords +
                '}';
    }
}
