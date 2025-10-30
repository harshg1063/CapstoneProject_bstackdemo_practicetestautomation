from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from pages.base_page import BasePage
import time


class LoginPage(BasePage):
    def __init__(self, driver):
        super().__init__(driver)
        self.url = "https://bstackdemo.com/"
        self.sign_in_button = (By.ID, "signin")
        self.username_dropdown = (By.CSS_SELECTOR, "div#username div.css-1hwfws3")  # clickable div
        self.password_dropdown = (By.CSS_SELECTOR, "div#password div.css-1hwfws3")
        self.login_button = (By.ID, "login-btn")
        self.products_container = (By.CLASS_NAME, "shelf-item")

    def login(self, username="demouser", password="testingisfun99"):
        """Perform login on BrowserStack Demo"""
        self.open(self.url)
        self.wait.until(EC.element_to_be_clickable(self.sign_in_button)).click()

        # Wait for login modal to appear
        self.wait.until(EC.visibility_of_element_located(self.username_dropdown))
        time.sleep(1)

        # Select username
        self.driver.find_element(*self.username_dropdown).click()
        username_option = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, f"//div[contains(@id,'react-select-2-option-0-0') or text()='{username}']"))
        )
        ActionChains(self.driver).move_to_element(username_option).click().perform()

        # Select password
        self.driver.find_element(*self.password_dropdown).click()
        password_option = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, f"//div[contains(@id,'react-select-3-option-0-0') or text()='{password}']"))
        )
        ActionChains(self.driver).move_to_element(password_option).click().perform()

        # Click Login
        self.wait.until(EC.element_to_be_clickable(self.login_button)).click()

        # Wait for home page products
        self.wait.until(EC.presence_of_all_elements_located(self.products_container))
        print(f"✅ Logged in successfully as {username}")
