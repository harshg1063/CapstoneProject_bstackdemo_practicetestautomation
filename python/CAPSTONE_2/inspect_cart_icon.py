from utils.base_driver import get_driver
from pages.login_page import LoginPage
from pages.home_page import HomePage
from pages.cart_page import CartPage
from selenium.webdriver.common.by import By

def inspect_cart_icon():
    driver = get_driver("chrome")
    login = LoginPage(driver)
    home = HomePage(driver)
    cart = CartPage(driver)
    try:
        login.login()
        home.wait_for_homepage()
        cart.add_multiple_items(3)
        # Now inspect the cart icon before trying to click
        print("Inspecting cart icon after adding items...")
        try:
            cart_icon = driver.find_element(By.CSS_SELECTOR, "div.float-cart__toggle")
            print(f"Cart icon found: tag={cart_icon.tag_name}, class={cart_icon.get_attribute('class')}, id={cart_icon.get_attribute('id')}, text={cart_icon.text}, displayed={cart_icon.is_displayed()}, enabled={cart_icon.is_enabled()}")
        except Exception as e:
            print(f"Cart icon not found: {e}")
        # Check for any cart-related elements
        try:
            cart_elements = driver.find_elements(By.CSS_SELECTOR, "[class*='cart'], [id*='cart'], [data-testid*='cart']")
            print(f"Potential cart elements found: {len(cart_elements)}")
            for elem in cart_elements:
                print(f"Cart element: tag={elem.tag_name}, class={elem.get_attribute('class')}, id={elem.get_attribute('id')}, text={elem.text}, displayed={elem.is_displayed()}")
        except Exception as e:
            print(f"Error finding cart elements: {e}")
        # Check if there's a cart badge or counter
        try:
            badges = driver.find_elements(By.CSS_SELECTOR, "[class*='badge'], [class*='count'], [class*='quantity']")
            print(f"Badge elements found: {len(badges)}")
            for badge in badges:
                print(f"Badge: tag={badge.tag_name}, class={badge.get_attribute('class')}, text={badge.text}")
        except Exception as e:
            print(f"Error finding badges: {e}")
        # Check the entire page source for cart-related text
        page_source = driver.page_source.lower()
        if 'cart' in page_source:
            print("Page contains 'cart' text")
        else:
            print("Page does not contain 'cart' text")
    finally:
        driver.quit()

if __name__ == "__main__":
    inspect_cart_icon()
