package lens.inmo360.daos;

import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;
import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import org.hamcrest.Matchers;
import org.hamcrest.core.StringContains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

import lens.inmo360.dtos.PropertyDTO;
import lens.inmo360.helpers.ModelHelper;
import lens.inmo360.managers.CouchBaseManager;
import lens.inmo360.model.Property;
import retrofit2.http.GET;
import static ch.lambdaj.Lambda.*;


/**
 * Created by Nach on 5/7/2016.
 */
public class PropertiesDAO {
    private static CouchBaseManager cbManager = new CouchBaseManager();
    public static ArrayList<Property> GetAll(){

        ArrayList<Property> res = new ArrayList<>();
        ArrayList<Document> docs = cbManager.getAllDocs();

        for (int i = 0; i < docs.size(); i++) {
            res.add(ModelHelper.modelForDocument(docs.get(i),Property.class));
        }

        return res;
    }

    public static Property getById(String id){
        Document doc = cbManager.getDocument(id);
        Property prop= ModelHelper.modelForDocument(doc, Property.class);
        return prop;
    }

    public static Property getById(Integer id){
        return getById(id.toString());
    }

    public static ArrayList<Property> getBy (PropertyDTO parameters){

        ArrayList<Property> filteredProps = GetAll();
        ArrayList<Property> auxPropsList = new ArrayList<>();

        for(Iterator<Property> it = filteredProps.iterator(); it.hasNext();){
            Property prop = it.next();
            //Province filter
            if(parameters.getProvince() != null && !Objects.equals(prop.getProvince().getName(), parameters.getProvince())){
                //filteredProps.remove(prop);
                it.remove();
            }
            //Operation filter
            else if(parameters.getOperation() != null && !prop.getOperation().equals(parameters.getOperation())){
               // filteredProps.remove(prop);
                it.remove();
            }
            //Rooms filter
            else if(parameters.getRooms() != null && prop.getRooms() != parameters.getRooms()){
                //filteredProps.remove(prop);
                it.remove();
            }
            //Min Price Filter
            else if(parameters.getMinPrice() != null && prop.getPrice() < parameters.getMinPrice()){
                //filteredProps.remove(prop);
                it.remove();
            }
            //Max price filter
            else if(parameters.getMaxPrice() != null && prop.getPrice() > parameters.getMaxPrice()){
                //filteredProps.remove(prop);
                it.remove();
            }
            //Location filter
            else if(parameters.getLocation() != null && parameters.getLocation().length > 0){
                ArrayList<String> locs = new ArrayList<>(Arrays.asList(parameters.getLocation()));
                if(!locs.contains(prop.getCity().getName())){
                    //filteredProps.remove(prop);
                    it.remove();
                }
            }
            //Category filter
            else if(parameters.getCategory() != null && parameters.getCategory().length > 0){
                ArrayList<String> cats = new ArrayList<>(Arrays.asList(parameters.getCategory()));
                if (!cats.contains(prop.getCategory())){
                    //filteredProps.remove(prop);
                    it.remove();
                }
            }
        }

//        if(parameters.getTitle() != null)
//            filteredProps = (ArrayList<Property>) select(filteredProps,having(on(Property.class).getTitle(), StringContains.containsString(parameters.getTitle())));
//
//        if(parameters.getProvince() != null)
//            filteredProps = (ArrayList<Property>) select(filteredProps,having(on(Property.class).getProvince(), StringContains.containsString(parameters.getProvince())));
//
//        if(parameters.getOperation() != null)
//            filteredProps = (ArrayList<Property>) select(filteredProps,having(on(Property.class).getOperation(), StringContains.containsString(parameters.getOperation())));
//
//        if(parameters.getRooms() != null){
//            auxPropsList.clear();
//            for(Property prop : filteredProps){
//                if(prop.getRooms() == parameters.getRooms())
//                    auxPropsList.add(prop);
//            }
//            filteredProps = auxPropsList;
//            //filteredProps = (ArrayList<Property>) select(filteredProps,having(on(Property.class).getRooms(), Matchers.equalTo(parameters.getRooms())));
//        }
//
//
//        String[] locations = parameters.getLocation();
//        if(locations != null && locations.length > 0){
//            ArrayList<Property> auxProps = filteredProps;
//
//            for (int i = 0; i < locations.length; i++){
//                auxProps.addAll( select(filteredProps,having(on(Property.class).getLocation(), StringContains.containsString(locations[i]))));
//            }
//            filteredProps = auxProps;
//        }
//
//        String[] categories = parameters.getCategory();
//        if(categories != null && categories.length > 0){
//            ArrayList<Property> auxProps = filteredProps;
//
//            for (int i = 0; i < categories.length; i++){
//                List<Property> cats = select(filteredProps,having(on(Property.class).getCategory(), StringContains.containsString(categories[i])));
//                auxProps.addAll(cats);
//            }
//            filteredProps = auxProps;
//        }
//
//        if (parameters.getMinPrice() != null && parameters.getMinPrice() > 0){
//            filteredProps = (ArrayList<Property>) select(filteredProps,having(on(Property.class).getPrice(), Matchers.greaterThan(parameters.getMinPrice())));
//        }
//
//        if (parameters.getMaxPrice() != null && parameters.getMaxPrice() > 0){
//            filteredProps = (ArrayList<Property>) select(filteredProps, having(on(Property.class).getPrice(), Matchers.lessThanOrEqualTo(parameters.getMaxPrice())));
//        }

        return filteredProps;
    }
}
