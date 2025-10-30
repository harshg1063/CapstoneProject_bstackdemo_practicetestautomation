from utils.base_driver import get_driver
from pages.login_page import LoginPage
from pages.home_page import HomePage
from pages.cart_page import CartPage

def inspect_dom_after_add():
    driver = get_driver("chrome")
    login = LoginPage(driver)
    home = HomePage(driver)
    cart = CartPage(driver)
    try:
        login.login()
        home.wait_for_homepage()
        cart.add_multiple_items(3)
        # Now inspect the DOM after adding items
        print("Page source after adding items:")
        print(driver.page_source[:2000])  # First 2000 chars to avoid too much output
        # Check for cart icon
        try:
            cart_elements = driver.find_elements_by_css_selector("[class*='cart'], [id*='cart'], [data-testid*='cart']")
            print(f"Potential cart elements found: {len(cart_elements)}")
            for elem in cart_elements:
                print(f"Cart element: tag={elem.tag_name}, class={elem.get_attribute('class')}, id={elem.get_attribute('id')}, text={elem.text}, displayed={elem.is_displayed()}")
        except Exception as e:
            print(f"Error finding cart elements: {e}")
        # Check for float-cart specifically
        try:
            float_cart = driver.find_elements_by_css_selector("div.float-cart__toggle")
            print(f"Float cart toggle found: {len(float_cart)}")
            if float_cart:
                print(f"Float cart: class={float_cart[0].get_attribute('class')}, visible={float_cart[0].is_displayed()}")
        except Exception as e:
            print(f"Error finding float cart: {e}")
        # Check for cart items directly
        try:
            cart_items = driver.find_elements_by_css_selector("div.float-cart__shelf-container div.shelf-item")
            print(f"Cart items found: {len(cart_items)}")
        except Exception as e:
            print(f"Error finding cart items: {e}")
        # Check if cart is open
        try:
            open_cart = driver.find_elements_by_css_selector("div.float-cart")
            print(f"Float cart container found: {len(open_cart)}")
            if open_cart:
                print(f"Float cart displayed: {open_cart[0].is_displayed()}")
        except Exception as e:
            print(f"Error finding float cart container: {e}")
    finally:
        driver.quit()

if __name__ == "__main__":
    inspect_dom_after_add()
