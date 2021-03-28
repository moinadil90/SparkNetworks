@startuml

namespace app {
        namespace di {
            interface QuestionComponent

            class QuestionModule

            QuestionComponent *-- QuestionModule
        }
        namespace presentation {
                namespace view {
                class SplashScreen {
                }
                class PersonalityActivity {
                }
                class PersonalityFragment {
                }
                class PersonalityAdapter {
                }
                }

                namespace viewmodel {
                interface PersonalityViewModelContract {
                    + save(personalityObject: JSONObject): Completable
                    +fetchQuestionsFromDB(): Single<List<QuestionRecord>>
                }
                class PersonalityViewModel {
                    - personalityInteractor: PersonalityInteractorContract
                }

                }
        }

        namespace domain {

                interface PersonalityInteractorContract {
                       + save(personalityObject: JSONObject): Completable
                       + fetchQuestionsFromDB(): Single<List<QuestionRecord>>
                }
                class PersonalityInteractor {
                }
        }

        namespace model {
            namespace repository {
                interface PersonalityRepositoryContract {
                        + save(personalityObject: JSONObject): Completable
                        + fetchQuestionsFromDB(): Single<List<QuestionRecord>>
                }
                class PersonalityRepository {
                }
            }

            namespace storage {
                 interface PersonalityStorageContract {
                          + save(personalityObject: JSONObject): Completable
                          + fetchQuestionsFromDB(): Single<List<QuestionRecord>>
                 }
                 class PersonalityStorage {
                     - personalityDAO: PersonalityDAOContract
                 }
                 namespace dao {
                       interface PersonalityDAOContract {
                                 + save(personalityObject: JSONObject): Completable
                                 + getQuestions(): Single<List<QuestionRecord>>
                       }
                       class PersonalityDAO {
                                 - persistenceStorage: PersistenceStorageContract
                       }
                 }
                 namespace records {
                         class QuestionRecord {
                            - question: String?
                            - question_type: QuestionType?
                            - category: String?
                            - id: Int
                        }
                         class QuestionType {
                            - options: RealmList<String>?
                            - type: String? = null
                            - selectedValue = ""
                        }
                 }
            }
        }
    }

app.presentation.view.PersonalityFragment o-- app.presentation.viewmodel.PersonalityViewModelContract
app.presentation.viewmodel.PersonalityViewModelContract <|.. app.presentation.viewmodel.PersonalityViewModel

app.presentation.viewmodel.PersonalityViewModel o-- app.domain.PersonalityInteractorContract
app.domain.PersonalityInteractorContract <|.. app.domain.PersonalityInteractor

app.domain.PersonalityInteractor o-- app.model.repository.PersonalityRepositoryContract
app.model.repository.PersonalityRepositoryContract <|.. app.model.repository.PersonalityRepository

app.model.repository.PersonalityRepository o-- app.model.storage.PersonalityStorageContract
app.model.storage.PersonalityStorageContract <|.. app.model.storage.PersonalityStorage

app.model.storage.PersonalityStorage o-- app.model.storage.dao.PersonalityDAOContract
app.model.storage.dao.PersonalityDAOContract <|.. app.model.storage.dao.PersonalityDAO

@enduml