package com.example.cuddly_octo_sniffle.data;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Sqlite_Utils {
    // the name of the database :)
    static final String DATABASE_NAME = "db_room_management";
    // --|--|--|--|--|--|--|--|--|--|--/--/--/--/--/--/--/--/--/--/--/--|--|--|--|--|--|--|--|--|--|--

        // BUILDING SQLite TABLE
    static final String TABLE_BUILDING_NAME = "tbl_building"; // name of the table
    static final String COLUMN_BUILDING_ID = "building_id"; // column ID
    static final String COLUMN_BUILDING_NAME = "building_name"; // column NAME
    static final String COLUMN_BUILDING_TYPE = "building_type"; // column TYPE
        // Add More Columns Here [ If you Want ]


    // --|--|--|--|--|--|--|--|--|--|--/--/--/--/--/--/--/--/--/--/--/--|--|--|--|--|--|--|--|--|

        // ROOM SQLite TABLE
    static final String TABLE_ROOM_NAME = "tbl_room"; // name of the table
    static final String COLUMN_ROOM_ID = "room_id"; // column ID
    static final String COLUMN_ROOM_NAME = "room_name"; // column NAME
    static final String COLUMN_ROOM_TYPE = "room_type"; // column TYPE
    static final String COLUMN_ROOM_OCCUPANT_NAME = "room_occurrence_name"; // column Occupant Name
    static final String COLUMN_ROOM_DESCRIPTION = "room_description"; // column Description
        //  Add More Columns Here [ If You Want ]

    // --|--|--|--|--|--|--|--|--|--|--/--/--/--/--/--/--/--/--/--/--/--|--|--|--|--|--|--|--|--|






    // --|--|--|--|--|--|--|--|--|--|--|--/--/--/--/--/--/--/--/--/--/--/--|--|--|--|--|--|--|--|

    // Copied from old code, might not work :D

    // Create The Tables if They Don't Exist
    public static void implementTables(@NonNull SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_BUILDING_NAME +  // table name <--
                " (" +
                COLUMN_BUILDING_ID +   // building id <--
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BUILDING_NAME + // building name <--
                " TEXT NOT NULL, " +
                COLUMN_BUILDING_TYPE + // building type <--
                " TEXT NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                TABLE_ROOM_NAME +  // table name <--
                " (" +
                COLUMN_ROOM_ID +   // room id <--
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROOM_NAME + // room name <--
                " TEXT NOT NULL, " +
                COLUMN_ROOM_TYPE + // room type <--
                " TEXT NOT NULL, " +
                COLUMN_ROOM_OCCUPANT_NAME + // room occurrence name <--
                " TEXT NOT NULL, " +
                COLUMN_ROOM_DESCRIPTION + // room description <--
                " TEXT NOT NULL);");
    }

    public static void insertToTables(SQLiteDatabase db){

        //ArrayList<Building_Information> building_informationArrayList =
        /*      !
            Implement the following after finishing
                build_Building_Information & build_Room_Information
                                                            !
         */


    }
    public static ArrayList<Building_Information> build_Buildings_List(){
        ArrayList<Building_Information> list = new ArrayList<Building_Information>();
        /* The following should take information from the firebase database
        Building_Information b0 = Building_Information();
        list.add(b0);
        return list;

         */
        return null; // Placeholder for no errors :3c
    }

    public static ArrayList<Room_Information> build_Rooms_List(){

        ArrayList<Room_Information> list = new ArrayList<Room_Information>();
        /* The following should take information from the firebase database
        Room_Information r0 = Room_Information();
        list.add(r0);
        return list;

         */
        return null; // Placeholder for no errors :3c
    }
}
