package com.example.grocery.apps

class Endpoint {
    companion object {
        private const val URL_CATEGORY = "category"
        private const val URL_SUBCATEGORY = "subcategory/"
        private const val URL_PRODUCT_BY_SUB_ID = "products/sub/"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_ADDRESS = "address/"
        private const val URL_ORDER = "orders/"

        fun getCategory(): String {
            return "${Config.BASE_URL + URL_CATEGORY}"
        }

        fun getSubCategoryByCatId(catId: Int): String {
            return "${Config.BASE_URL + URL_SUBCATEGORY + catId}"
        }

        fun getProductBySubId(subId: Int): String {
            return "${Config.BASE_URL + URL_PRODUCT_BY_SUB_ID + subId}"
        }

        fun getImage(imgString: String): String {
            return "${Config.IMAGE_URL}$imgString"
        }

        fun getRegister(): String {
            return "${Config.BASE_URL + URL_REGISTER}"
        }

        fun getLogin(): String {
            return "${Config.BASE_URL + URL_LOGIN}"
        }

        fun getAddressById(userId: String): String {
            return "${Config.BASE_URL + URL_ADDRESS}$userId"
        }

        fun postAddress(): String {
            return "${Config.BASE_URL + URL_ADDRESS}"
        }

        fun getOrder(): String {
            return "${Config.BASE_URL + URL_ORDER}"
        }

        fun getOrderByUserId(uid : String) : String{
            return "${Config.BASE_URL + URL_ORDER}$uid"
        }
    }
}

