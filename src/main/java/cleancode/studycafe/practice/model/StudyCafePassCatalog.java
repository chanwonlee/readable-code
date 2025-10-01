package cleancode.studycafe.practice.model;

import java.util.List;

public record StudyCafePassCatalog(List<StudyCafePass> passes) {

    public int getCatalogSize() {
        return passes.size();
    }

    public StudyCafePass getPass(int index) {
        return passes.get(index);
    }
}
