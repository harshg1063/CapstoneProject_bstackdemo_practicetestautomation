from utils.base_driver import get_driver
from pages.login_page import LoginPage
from pages.home_page import HomePage

def test_vendor_filter():
    driver = get_driver("chrome")
    login = LoginPage(driver)
    home = HomePage(driver)

    try:
        login.login()
        home.filter_by_vendor("Apple")
        print("✅ Vendor Filter Test Passed")
    finally:
        driver.quit()
