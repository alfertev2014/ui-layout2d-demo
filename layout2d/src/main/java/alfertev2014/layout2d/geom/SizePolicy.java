package alfertev2014.layout2d.geom;

public interface SizePolicy {
    boolean isGrowing();
    boolean isShrinking();
    boolean isExpanding();

    default boolean isFixed() {
        return ! (isShrinking() || isGrowing());
    }

    static SizePolicy of(boolean growing, boolean shrinking, boolean expanding) {
        return new SizePolicy() {
            @Override
            public boolean isGrowing() {
                return growing;
            }

            @Override
            public boolean isShrinking() {
                return shrinking;
            }

            @Override
            public boolean isExpanding() {
                return expanding;
            }
        };
    }

    static SizePolicy expanding() {
        return of(true, true, true);
    }

    static SizePolicy minimumExpanding() {
        return of(true, false, true);
    }

    static SizePolicy maximumExpanding() {
        return of(false, true, true);
    }

    static SizePolicy minimum() {
        return of(true, false, false);
    }

    static SizePolicy maximum() {
        return of(false, true, false);
    }

    static SizePolicy preferred() {
        return of(true, true, false);
    }

    static SizePolicy fixed() {
        return of(false, false, false);
    }
}
