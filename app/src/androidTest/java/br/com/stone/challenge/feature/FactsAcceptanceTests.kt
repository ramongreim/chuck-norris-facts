package br.com.stone.challenge.feature

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.stone.challenge.R
import br.com.stone.challenge.feature.base.BaseTest
import br.com.stone.challenge.feature.facts.FactsActivity
import br.com.stone.challenge.feature.screen.FactsScreen
import br.com.stone.challenge.feature.screen.SearchScreen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FactsAcceptanceTests: BaseTest<FactsActivity>(FactsActivity::class) {

    @Test
    fun acceptDefaultLayout() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            emptyLayout.isVisible()

            txtEmptyTitle {
                isVisible()
            }

            btnSearchFact.click()
        }
    }

    @Test
    fun acceptSearchTerm() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit.typeText("test")

                isErrorDisabled()

                edit.pressImeAction()
            }
        }

        onScreen<FactsScreen> {
            emptyLayout.isGone()

            recyclerFacts {
                isVisible()
                hasSize(53)
            }
        }
    }

    @Test
    fun acceptSearchValidateError() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit.typeText("a")

                isErrorEnabled()
                hasError("This field must have more characters")
            }
        }
    }

    @Test
    fun acceptHistoricTerm() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            recyclerHistoric {
                firstChild<SearchScreen.Item> {
                    click()
                }
            }
        }

        onScreen<FactsScreen> {
            emptyLayout.isGone()

            recyclerFacts {
                isVisible()
                hasSize(53)
            }
        }
    }

    @Test
    fun acceptSuggestions() {
        mockServer.simulate {
            allCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            suggestionTagView.click()
        }

        onScreen<FactsScreen> {
            emptyLayout.isGone()

            recyclerFacts {
                isVisible()
                hasSize(53)
            }
        }
    }

    @Test
    fun acceptNetworkError() {
        mockServer.simulate {
            factsNetworkError()
            categoriesCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit {
                    typeText("test")
                    pressImeAction()
                }
            }
        }

        onScreen<FactsScreen> {
            errorView.isVisible()
            txtViewError.hasText(R.string.error_network)
            imgViewError.hasDrawable(R.drawable.ic_network_error)
        }
    }

    @Test
    fun acceptServerError() {
        mockServer.simulate {
            factsServerError()
            categoriesCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit {
                    typeText("test")
                    pressImeAction()
                }
            }
        }

        onScreen<FactsScreen> {
            errorView.isVisible()
            txtViewError.hasText(R.string.error_server)
            imgViewError.hasDrawable(R.drawable.ic_server_error)
        }
    }

    @Test
    fun acceptGenericError() {
        mockServer.simulate {
            factsGenericError()
            categoriesCorrect()
        }

        startActivity()

        onScreen<FactsScreen> {
            btnSearchFact.click()
        }

        onScreen<SearchScreen> {
            inputLayoutSearch {
                edit {
                    typeText("test")
                    pressImeAction()
                }
            }
        }

        onScreen<FactsScreen> {
            errorView.isVisible()
            txtViewError.hasText(R.string.error_generic)
            imgViewError.hasDrawable(R.drawable.ic_generic_error)
        }
    }

}