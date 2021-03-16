package alfertev2014.layout2d.geom;

public interface Alignment {

    boolean isLeading();
    boolean isTrailing();

    static Alignment leading() {
        return of(true, false);
    }

    static Alignment trailing() {
        return of(false, true);
    }

    static Alignment center() {
        return of(false, false);
    }

    static Alignment justify() {
        return of(true, true);
    }

    static Alignment of(boolean leading, boolean trailing) {
        return new Alignment() {
            @Override
            public boolean isLeading() {
                return leading;
            }

            @Override
            public boolean isTrailing() {
                return trailing;
            }
        };
    }
}
