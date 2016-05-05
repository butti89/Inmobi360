package lens.inmo360.managers;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Nach on 5/1/2016.
 */
public class CouchBaseManager {

    final String TAG = "Inmobi360";

    //couch internals
    protected static Manager manager;
    private Database database;

    public void InitCBL(AndroidContext ac){
        try {

            Log.d(TAG, "Begin CBL");

            manager = new Manager(ac, Manager.DEFAULT_OPTIONS);
            Log.d (TAG, "Manager created");
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        // create a name for the database and make sure the name is legal
        String dbname = "inmobiCBL";
        if (!Manager.isValidDatabaseName(dbname)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        // create a new database
        try {
            database = manager.getDatabase(dbname);
            Log.d (TAG, "Database created");

        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return;
        }
    }

    public Document CreateDocument(String id){
        return database.getDocument(id);
    }

    public Document CreateDocument(){
        return database.createDocument();
    }

    public Boolean WriteDocument(Document doc, Map properties){
        try {
            doc.putProperties(properties);
            Log.d(TAG, "updated retrievedDocument=" + String.valueOf(doc.getProperties()));
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Cannot update document", e);
            return false;
        }
        return true;
    }

    public Boolean WriteDocument(String docId, Map properties){
        return WriteDocument(database.getExistingDocument(docId),properties);
    }

    public Map GetDocumentProperties(Document doc){
        return doc.getProperties();
    }

    public Map GetDocumentProperties(String docId){
        return GetDocumentProperties(database.getExistingDocument(docId));
    }

    //TODO: UPDATE PROPERTIES
}
