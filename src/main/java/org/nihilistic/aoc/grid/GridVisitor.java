package org.nihilistic.aoc.grid;

public record GridVisitor (String input) {
    public void visit(VisitableGrid visitableGrid) {
        var x=0;
        var y=0;
        for (var i=0; i < input.length(); i++) {
            var character = input.charAt(i);
            if (character == '\n') {
                y++;
                x=0;
                continue;
            }
            visitableGrid.visit(x, y, character);
            x++;
        }
    }
}
