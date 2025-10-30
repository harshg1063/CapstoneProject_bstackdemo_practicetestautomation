from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.edge.service import Service as EdgeService
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.microsoft import EdgeChromiumDriverManager

def get_driver(browser="chrome", cloud=False):
    if browser.lower() == "chrome":
        options = webdriver.ChromeOptions()
        options.add_argument("--start-maximized")
        driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)
    elif browser.lower() == "edge":
        options = webdriver.EdgeOptions()
        driver = webdriver.Edge(service=EdgeService(EdgeChromiumDriverManager().install()), options=options)
    else:
        raise ValueError("Unsupported browser!")

    driver.implicitly_wait(5)
    return driver
