package code;

public class Product {
	
	
	private int ProductId;
	private int Price;
	private String ProductType;
	private String ImageSource;
	
	public enum ProductType{
		Meat,
		Grain,
		Milk,
		Drink,
		Bakery
	}
	
	public Product(){}
	
	public Product(int productId, int price, String productType, String imageSource) {
		super();
		ProductId = productId;
		Price = price;
		ProductType = productType;
		ImageSource = imageSource;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getProductType() {
		return ProductType;
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	public String getImageSource() {
		return ImageSource;
	}
	public void setImageSource(String imageSource) {
		ImageSource = imageSource;
	}
	
}
