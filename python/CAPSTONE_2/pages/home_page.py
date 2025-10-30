from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from pages.base_page import BasePage
import time

class HomePage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.product_items = (By.CSS_SELECTOR, "div.shelf-item")
        self.add_to_cart_buttons = (By.CSS_SELECTOR, "div.shelf-item__buy-btn")
        self.vendor_buttons = (By.CSS_SELECTOR, "div.filters-available-size span.checkmark")

    def wait_for_homepage(self):
        """Wait until product grid is visible."""
        print("⏳ Waiting for homepage to load...")
        self.wait.until(EC.presence_of_all_elements_located(self.product_items))
        print("✅ Homepage loaded successfully with products.")

    def add_to_favourites(self, count=2):
        """Simulate adding items to favourites (Add to cart button as placeholder)."""
        self.wait_for_homepage()
        buttons = self.wait.until(EC.presence_of_all_elements_located(self.add_to_cart_buttons))
        if not buttons:
            raise Exception("❌ No product buttons found on homepage.")
        for i, button in enumerate(buttons[:count]):
            self.driver.execute_script("arguments[0].scrollIntoView(true);", button)
            self.driver.execute_script("arguments[0].click();", button)
            print(f"❤️ Added product {i+1} to favourites (simulated).")
            time.sleep(1)
        print(f"✅ Added {count} products to favourites successfully.")

    def filter_by_vendor(self, vendor_name):
        """Click on vendor filter button (Apple, Samsung, etc.)."""
        self.wait_for_homepage()
        vendor_buttons = self.wait.until(EC.presence_of_all_elements_located(self.vendor_buttons))
        found = False
        for button in vendor_buttons:
            if vendor_name.lower() in button.text.lower():
                ActionChains(self.driver).move_to_element(button).click().perform()
                found = True
                print(f"✅ Vendor filter applied: {vendor_name}")
                break
        if not found:
            raise Exception(f"❌ Vendor '{vendor_name}' button not found on page.")
        time.sleep(3)
