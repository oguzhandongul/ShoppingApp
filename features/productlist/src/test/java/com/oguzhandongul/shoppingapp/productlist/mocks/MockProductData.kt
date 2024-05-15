package com.oguzhandongul.shoppingapp.productlist.mocks

import com.oguzhandongul.shoppingapp.product.model.CartItem
import com.oguzhandongul.shoppingapp.product.model.Price
import com.oguzhandongul.shoppingapp.product.model.Product
import com.oguzhandongul.shoppingapp.product.model.ProductInfo

object MockProductData {

    fun getMockProduct(id: String): Product {
        return when (id) {
            "1" -> Product(
                "1",
                "Henriksdal",
                Price(499.0, "kr"),
                ProductInfo("wood with cover", "white", null),
                "chair",
                "https://www.ikea.com/pimg/0462849_PE608354_S4.JPG"
            )

            "2" -> Product(
                "2",
                "Lidhult",
                Price(1035.0, "kr"),
                ProductInfo(null, "beige", 4),
                "couch",
                "https://www.ikea.com/pimg/0667779_PE714073_S4.JPG"
            )

            "3" -> Product(
                "3",
                "Nyhamn",
                Price(3395.0, "kr"),
                ProductInfo(null, "gray", 3),
                "couch",
                "https://www.ikea.com/pimg/0556706_PE660542_S4.JPG"
            )

            "4" -> Product(
                "4",
                "Landskrona",
                Price(7495.0, "kr"),
                ProductInfo(null, "black", 2),
                "couch",
                "https://www.ikea.com/pimg/0320948_PE514802_S4.JPG"
            )

            "5" -> Product(
                "5",
                "Odger",
                Price(695.20, "kr"),
                ProductInfo("plastic", "dark blue", null),
                "chair",
                "https://www.ikea.com/pimg/0727322_PE735594_S4.JPG"
            )

            "6" -> Product(
                "6",
                "Landskrona",
                Price(14396.0, "kr"),
                ProductInfo(null, "black", 5),
                "couch",
                "https://www.ikea.com/pimg/0630092_PE694645_S4.JPG"
            )

            "7" -> Product(
                "7",
                "Klippan",
                Price(1995.0, "kr"),
                ProductInfo(null, "multi", 2),
                "couch",
                "https://www.ikea.com/pimg/0655887_PE709150_S4.JPG"
            )

            "8" -> Product(
                "8",
                "Kullaberg",
                Price(499.0, "kr"),
                ProductInfo("wood and metal", "black", null),
                "chair",
                "https://www.ikea.com/pimg/0724693_PE734575_S4.JPG"
            )

            "9" -> Product(
                "9",
                "Stocksund",
                Price(4995.0, "kr"),
                ProductInfo(null, "flanell", 3),
                "couch",
                "https://www.ikea.com/pimg/0617825_PE688232_S4.JPG"
            )

            "10" -> Product(
                "10",
                "IKEA PS 2012",
                Price(495.0, "kr"),
                ProductInfo("wood", "black", null),
                "chair",
                "https://www.ikea.com/pimg/0154688_PE312833_S4.JPG"
            )

            "11" -> Product(
                "11",
                "Henriksdal",
                Price(1195.0, "kr"),
                ProductInfo("wood with cover", "black", null),
                "chair",
                "https://www.ikea.com/pimg/0728320_PE736188_S4.JPG"
            )

            "12" -> Product(
                "12",
                "Industriell",
                Price(499.0, "kr"),
                ProductInfo("wood", "wood", null),
                "chair",
                "https://www.ikea.com/pimg/0579780_PE669907_S4.JPG"
            )

            "13" -> Product(
                "13",
                "Janinge",
                Price(395.0, "kr"),
                ProductInfo("plastic", "white", null),
                "chair",
                "https://www.ikea.com/pimg/0728156_PE736116_S4.JPG"
            )

            "14" -> Product(
                "14",
                "Ektorp",
                Price(4495.0, "kr"),
                ProductInfo(null, "multi", 3),
                "couch",
                "https://www.ikea.com/pimg/0619738_PE689282_S4.JPG"
            )

            else -> throw IllegalArgumentException("Unknown product ID")
        }
    }

    fun getMockProductList(): List<Product> {
        return listOf(
            getMockProduct("1"),
            getMockProduct("2"),
            getMockProduct("3"),
            getMockProduct("4"),
            getMockProduct("5"),
            getMockProduct("6"),
            getMockProduct("7"),
            getMockProduct("8"),
            getMockProduct("9"),
            getMockProduct("10"),
            getMockProduct("11"),
            getMockProduct("12"),
            getMockProduct("13"),
            getMockProduct("14")
        )
    }

    fun getMockCartItem(id: String, quantity: Int): CartItem {
        return CartItem(ids = 1, productId = id, product = getMockProduct(id), quantity = quantity)
    }
}