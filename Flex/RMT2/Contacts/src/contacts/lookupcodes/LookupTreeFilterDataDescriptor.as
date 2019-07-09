package contacts.lookupcodes {
	
import mx.collections.ICollectionView;
import mx.controls.treeClasses.DefaultDataDescriptor;

public class LookupTreeFilterDataDescriptor extends DefaultDataDescriptor {

    // The isBranch method simply returns true if the node is an
    // Object with a children field.
    // It does not support empty branches, but does support null children
    // fields.
    public override function isBranch(node:Object, model:Object=null):Boolean {
    	var hasProp : Boolean;
    	var result : Boolean = super.isBranch(node, model);
        try {
            if (node is Object) {
                if (node.children != null)  {
                	if (node.localName() == "group") {
                		return true;
                	}
                	if (node.localName() == "code") {
                		return false;
                	}
                }
            }
        }
        catch (e:Error) {
            trace("[Descriptor] exception checking for isBranch");
        }
        return false;
    }
 }
}

