from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from pages.base_page import BasePage
import time

class CartPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.product_items = (By.CSS_SELECTOR, "div.shelf-item")
        self.add_to_cart_buttons = (By.CSS_SELECTOR, "div.shelf-item__buy-btn")
        self.cart_icon = (By.CSS_SELECTOR, "div.float-cart__toggle")
        self.cart_items = (By.CSS_SELECTOR, "div.float-cart__shelf-container div.shelf-item")

    def add_multiple_items(self, count=3):
        """Add multiple visible items to the cart."""
        print("⏳ Waiting for product grid before adding to cart...")
        self.wait.until(EC.presence_of_all_elements_located(self.product_items))
        buttons = self.wait.until(EC.presence_of_all_elements_located(self.add_to_cart_buttons))
        print(f"✅ Found {len(buttons)} 'Add to cart' buttons.")

        for i, button in enumerate(buttons[:count]):
            self.driver.execute_script("arguments[0].scrollIntoView(true);", button)
            self.driver.execute_script("window.scrollBy(0, 100);")
            self.driver.execute_script("arguments[0].click();", button)
            print(f"🛒 Added item {i+1} to cart.")
            time.sleep(1)
        print(f"✅ Added {count} products to cart successfully.")

    def verify_cart_items(self):
        """Open cart and count number of items."""
        print("🧾 Opening cart to verify items...")

        try:
            possible_cart_locators = [
                (By.CSS_SELECTOR, "div.float-cart__toggle"),
                (By.CSS_SELECTOR, "span.bag--float-cart-closed"),
                (By.XPATH, "//div[contains(@class,'float-cart') and not(contains(@class,'open'))]")
            ]

            cart_icon = None
            for locator in possible_cart_locators:
                try:
                    cart_icon = self.wait.until(EC.presence_of_element_located(locator))
                    if cart_icon:
                        print(f"✅ Found cart icon using locator: {locator}")
                        break
                except Exception:
                    continue

            if not cart_icon:
                raise Exception("❌ Could not find cart icon even after checking multiple locators.")

            # Scroll to top (cart usually top-right)
            self.driver.execute_script("window.scrollTo(0, 0);")
            time.sleep(1)

            # Click via JS
            self.driver.execute_script("arguments[0].click();", cart_icon)
            print("🛍️ Clicked cart icon successfully.")
            time.sleep(2)

            # Wait for cart items to appear
            items = self.wait.until(EC.presence_of_all_elements_located(self.cart_items))
            print(f"🛒 Cart contains {len(items)} item(s).")
            return len(items)

        except Exception as e:
            print(f"❌ Could not open cart or verify items: {e}")
            self.driver.save_screenshot("cart_debug.png")
            raise
