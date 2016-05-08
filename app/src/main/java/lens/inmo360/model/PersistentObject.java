package lens.inmo360.model;

import lens.inmo360.helpers.ModelHelper;
import lens.inmo360.managers.CouchBaseManager;

/**
 * Created by Nach on 5/8/2016.
 */
public class PersistentObject {

    private CouchBaseManager couchBaseManager;

    public void save(){
        ModelHelper.save(couchBaseManager.getDataBase(),this);
    }

    public void delete(String id){
        couchBaseManager.deleteDocument(id);
    }
}
