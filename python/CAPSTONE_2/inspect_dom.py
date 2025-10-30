from utils.base_driver import get_driver
from pages.login_page import LoginPage

def inspect_dom():
    driver = get_driver("chrome")
    login = LoginPage(driver)
    try:
        login.login()
        # Print page source for inspection
        print("Page source after login:")
        print(driver.page_source)
        # Alternatively, find potential elements
        # For add to cart buttons
        try:
            buttons = driver.find_elements_by_css_selector("button")
            print("All button elements:")
            for btn in buttons[:10]:  # First 10
                print(f"Button: {btn.text}, class: {btn.get_attribute('class')}, id: {btn.get_attribute('id')}")
        except Exception as e:
            print(f"Error finding buttons: {e}")
        # For cart icon
        try:
            cart_elements = driver.find_elements_by_css_selector("[class*='cart'], [id*='cart'], [data-testid*='cart']")
            print(f"Potential cart elements found: {len(cart_elements)}")
            for elem in cart_elements:
                print(f"Cart element: tag={elem.tag_name}, class={elem.get_attribute('class')}, id={elem.get_attribute('id')}, text={elem.text}")
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
        # For vendor filters
        try:
            filters = driver.find_elements_by_css_selector("div.filters-available-size button")
            print(f"Vendor buttons found: {len(filters)}")
            for f in filters:
                print(f"Filter: {f.text}")
        except Exception as e:
            print(f"Error finding vendor filters: {e}")
    finally:
        driver.quit()

if __name__ == "__main__":
    inspect_dom()
