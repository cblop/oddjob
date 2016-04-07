(ns client.db)

(def default-db
  {:tabs [{:id :tab1 :label "Tropes"}
          {:id :tab2 :label "Timeline"}
          {:id :tab3 :label "Story"}]
   :current-tab :tab1
   :tropes [{:id "conflict" :label "Conflict" :archetypes ["hero" "villain"]}
            {:id "three-act-structure" :label "Three Act Structure"}
            {:id "the-reveal" :label "The Reveal"}
            {:id "the-climax" :label "The Climax"}
            {:id "the-end" :label "The End"}
            {:id "chekhovs-gun" :label "Chekhov's Gun"}]
   :archetypes [{:id "hero" :label "The Hero"}
                {:id "villain" :label "The Villain"}]
   :characters [{:id "luke-skywalker" :label "Luke Skywalker"}
                {:id "darth-vader" :label "Darth Vader"}]
   :our-tropes [nil]
   :timeline []
   :story ""})
