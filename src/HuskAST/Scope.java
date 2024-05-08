package HuskAST;

import java.util.HashMap;

public class Scope {

    private Scope parent;

    // HashMap for ID and Type
    private HashMap<String, String> idTypes;

    // private constructor, can only be initialised within the factory methods
    private Scope(Scope parent, boolean inheritIDTypes) {
        this.parent = parent;
        idTypes = new HashMap<>();

        if (inheritIDTypes) {
            idTypes.putAll(parent.idTypes);
        }
        else {  // only carries over functions
            for (var idType : parent.idTypes.entrySet()) {
                if (idType.getValue().equals("func")) {
                    idTypes.put(idType.getKey(), idType.getValue());
                }
            }
        }
    }

    private Scope() {
        this.parent = null;
        idTypes = new HashMap<>();
    }

    // accessors
    public Scope Parent() {
        return parent;
    }

    public HashMap<String, String> IDTypes() {
        return idTypes;
    }


    // factory methods
    public static Scope CreateBaseScope() {
        return new Scope();
    }

    public static Scope CreateFunctionScope(Scope parent) {
        return new Scope(parent, false);
    }

    public static Scope CreateConditionalScope(Scope parent) {
        return new Scope(parent, true);
    }
}
