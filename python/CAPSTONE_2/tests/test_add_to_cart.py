import pytest
from utils.base_driver import get_driver
from pages.login_page import LoginPage
from pages.home_page import HomePage
from pages.cart_page import CartPage

@pytest.mark.parametrize("browser", ["chrome"])
def test_add_to_cart(browser):
    driver = get_driver(browser)
    login = LoginPage(driver)
    home = HomePage(driver)
    cart = CartPage(driver)

    try:
        login.login()
        home.wait_for_homepage()
        cart.add_multiple_items(3)
        assert cart.verify_cart_items() >= 3
    finally:
        driver.quit()
