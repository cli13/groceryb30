package com.example.grocery.managers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.se.omapi.Session
import com.example.grocery.models.CartProduct
import com.example.grocery.models.Product

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "GroceryData"
        const val DATABASE_VERSION = 7
        const val CART_TABLE = "Cart"
        const val PRODUCT_ID = "Id"
        const val USER_ID = "User_id"
        const val PRODUCT_IMAGE = "Image"
        const val PRODUCT_NAME = "ProductName"
        const val QUANTITY = "Quantity"
        const val PRICE = "Price"
        const val MRP = "mrp"
    }

    private var db = writableDatabase
    private var sm = SessionManager(mContext)

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "create table $CART_TABLE ($PRODUCT_ID char(30), $USER_ID char(30), $PRODUCT_NAME char(50), " +
                    "$PRODUCT_IMAGE char(40), $QUANTITY integer, $PRICE decimal, $MRP decimal)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $CART_TABLE"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun onAddToCart(product: Product) {
        var contentValue = ContentValues()
        contentValue.put(PRODUCT_ID, product._id)
        contentValue.put(PRODUCT_NAME, product.productName)
        contentValue.put(QUANTITY, 1)
        contentValue.put(USER_ID, sm.getUserId())
        contentValue.put(MRP, product.mrp)
        contentValue.put(PRODUCT_IMAGE, product.image)
        contentValue.put(PRICE, product.price)
        db.insert(CART_TABLE, null, contentValue)
    }

    fun onEditCartQuantity(_id: String, Mode: Int) {
        var query = "select $QUANTITY from $CART_TABLE where $PRODUCT_ID = ? AND $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(_id, sm.getUserId()))
        var quantity = 0
        if (cursor != null && cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY))
            cursor.close()
            if (quantity == 1 && Mode < 0)
                return
        }
        var contentValue = ContentValues()
        contentValue.put(QUANTITY, quantity + Mode)
        var whereClause = "$PRODUCT_ID = ? AND $USER_ID = ?"
        var whereArgs = arrayOf(_id, sm.getUserId())
        db.update(CART_TABLE, contentValue, whereClause, whereArgs)
    }

    fun findProductIfExist(id: String): Boolean {
        var p: Product? = null
        var query = "SELECT * FROM $CART_TABLE WHERE $PRODUCT_ID = ? AND $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(id, sm.getUserId()))
        return cursor.moveToFirst()
    }

    fun getAllFromCart(): List<CartProduct> {
        var list = ArrayList<CartProduct>()
        var query = "SELECT * FROM $CART_TABLE WHERE $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(sm.getUserId()))

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var id = cursor.getString(cursor.getColumnIndex(PRODUCT_ID))
                var name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME))
                var image = cursor.getString(cursor.getColumnIndex(PRODUCT_IMAGE))
                var mrp = cursor.getDouble(cursor.getColumnIndex(MRP))
                var price = cursor.getDouble(cursor.getColumnIndex(PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY))
                var cartProduct = CartProduct(id, name, price, image, quantity, mrp)

                list.add(cartProduct)
            } while (cursor.moveToNext())
        }
        return list
    }

    fun deleteAllCartItems() {
        var whereClause = "$USER_ID = ?"
        db.delete(CART_TABLE, whereClause, arrayOf(sm.getUserId()))
    }

    fun deleteItemById(_id: String) {
        var whereClause = "$PRODUCT_ID = ? AND $USER_ID = ?"
        db.delete(CART_TABLE, whereClause, arrayOf(_id, sm.getUserId()))
    }

    fun getQuantityById(id: String, uid: String): Int {
        var query = "SELECT $QUANTITY FROM $CART_TABLE WHERE $PRODUCT_ID = ? AND $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(id, uid))
        if (cursor != null && cursor.moveToFirst()) {
            var q = cursor.getInt(cursor.getColumnIndex(QUANTITY))
            return q
        }
        return -1;
    }

    /**
     * @returns an array of len 2
     * arr[0] = price without discount
     * arr[1] = price with discount
     */
    fun getOrderSummary(): Array<Double> {
        var result: Array<Double> = arrayOf(0.0, 0.0)

        var query = "SELECT $MRP, $PRICE, $QUANTITY from $CART_TABLE WHERE $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(sm.getUserId()))

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var mrp = cursor.getDouble(cursor.getColumnIndex(MRP))
                var price = cursor.getDouble(cursor.getColumnIndex(PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY))

                result[0] += (quantity * mrp)
                result[1] += (quantity * price)
            } while (cursor.moveToNext())
        }
        return result
    }

    fun getCartNumber(): Int {
        var query = "SELECT COUNT(*) FROM $CART_TABLE WHERE $USER_ID = ?"
        var cursor = db.rawQuery(query, arrayOf(sm.getUserId()))
        if (cursor != null && cursor.moveToFirst()) {
            var q = cursor.getInt(0)
            return q
        }
        return 0
    }
}