package ca.bcit.infosys.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * Resources to display categories and choose one.
 * @author blink
 * @version 1
 *
 */
@Named("sheetControl")
@ConversationScoped
public class TimeSheetController  implements Serializable{
//    /** Hold ID used to reference category record. */
//    private int categoryID;
//    /** Manager for category objects.*/
//    @Inject private CategoryManager categoryManager;
//    /** mechanism for starting conversation. */
//    @Inject private Conversation conversation;
//    /**
//     * Construct list of SelectItems for all categories from database.
//     * @return array of all categories.
//     */
//    public Category[] getCategoryItems() {
//   //TESTCategory[] categories = {new Category(1,"One"), new Category(2,"two")};
//        Category[] categories = categoryManager.getAll();
//        return categories;
//    }
//    /**
//     * Action method for index form.
//     * @return next page selector.
//     */
//    public String action() {
//        //start conversation to handle products for one category
//        conversation.begin();
//        return "displayProduct";
//    }
//
//    /**
//     * categoryID accessor.
//     * @return the categoryID
//     */
//    public int getCategoryID() {
//        return categoryID;
//    }
//    
//    public String getName() {
//        Category c = categoryManager.find(categoryID);
//        if (c != null) {
//            return c.getName();
//        } else {
//            return "Not Found";
//        }
//    }
//
//    /**
//     * categoryID mutator.
//     * @param categoryID the categoryID to set
//     */
//    public void setCategoryID(int categoryID) {
//        this.categoryID = categoryID;
//    }
}
