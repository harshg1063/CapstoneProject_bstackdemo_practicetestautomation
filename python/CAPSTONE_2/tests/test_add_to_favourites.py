from utils.base_driver import get_driver
from pages.login_page import LoginPage
from pages.home_page import HomePage

def test_add_to_favourites():
    driver = get_driver("chrome")
    login = LoginPage(driver)
    home = HomePage(driver)

    try:
        login.login()
        home.add_to_favourites(2)
        print("✅ Favourites Test Passed")
    finally:
        driver.quit()
