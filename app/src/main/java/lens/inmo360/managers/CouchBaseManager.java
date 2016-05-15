package lens.inmo360.managers;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nach on 5/1/2016.
 */
public class CouchBaseManager {

    final static String TAG = "Inmobi360";
    public static final String DB_NAME = "inmobidb";

    //couch internals
    protected static Manager manager;
    private static Database database;

    public void initCBL(AndroidContext ac){
        try {

            Log.d(TAG, "Begin CBL");

            manager = new Manager(ac, Manager.DEFAULT_OPTIONS);
            Log.d (TAG, "Manager created");
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        // make sure the name is legal
        if (!Manager.isValidDatabaseName(DB_NAME)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        // create a new database
        try {
            database = manager.getDatabase(DB_NAME);
            Log.d (TAG, "Database created");

        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return;
        }
    }

    public static Database getDataBase(){
        if(database != null)
            return database;

        Manager manager = new Manager();
        try {
            database = manager.getDatabase(DB_NAME);
            Log.d (TAG, "Database created");

        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return null;
        }
        return database;
    }

    public Document createDocument(String id){
        return database.getDocument(id);
    }

    public Document createDocument(){
        return database.createDocument();
    }

    public Boolean writeDocument(Document doc, Map properties){
        try {
            doc.putProperties(properties);
            Log.d(TAG, "updated retrievedDocument=" + String.valueOf(doc.getProperties()));
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Cannot update document", e);
            return false;
        }
        return true;
    }

    public Boolean writeDocument(String docId, Map properties){
        return writeDocument(database.getExistingDocument(docId),properties);
    }

    public Document getDocument(String docId){
        return database.getExistingDocument(docId);
    }

    //Debug usage
    public static void seeAllDocs(){
        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
        QueryEnumerator result = null;
        try {
             result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            if (row.getConflictingRevisions().size() > 0) {
                Log.w("MYAPP", "Conflict in document: %s", row.getDocumentId());
            }
        }
    }

    public static ArrayList<Document>  getAllDocs(){
        ArrayList<Document> documents = new ArrayList<>();

        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            documents.add(row.getDocument());
        }
        return documents;
    }

    public Map getDocumentProperties(Document doc){
        return doc.getProperties();
    }

    public Map getDocumentProperties(String docId){
        return getDocumentProperties(database.getExistingDocument(docId));
    }

    public void deleteDocument(String id){
        Document doc = database.getExistingDocument(id);
        deleteDocument(doc);
    }

    public void deleteDocument(Document doc){
        try {
            doc.delete();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    //TODO: UPDATE PROPERTIES
}
