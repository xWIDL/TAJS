package dk.brics.tajs.xwidl;

/**
 * Created by zz on 16-10-21.
 */
public enum RelBiOp {
    NotEqual("NotEqual"),
    Equal("Equal"),
    And("And"),
    Or("Or"),
    LessThan("LessThan"),
    LessEq("LessEq"),
    GreaterThan("GreaterThan"),
    GreaterEq("GreaterEq");

    private String name;

    RelBiOp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
