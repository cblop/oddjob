(ns client.db)

(def default-db
  {:tabs [{:id :tab1 :label "Tropes"}
          {:id :tab2 :label "Timeline"}
          {:id :tab3 :label "Story"}]
   :current-tab :tab1
   :tropes []
   :archetypes []
   :characters []
   :our-tropes [nil]
   :timeline []
   :show false
   :story ""})


;; (def default-db
;;   {:tabs [{:id :tab1 :label "Tropes"}
;;           {:id :tab2 :label "Timeline"}
;;           {:id :tab3 :label "Story"}]
;;    :current-tab :tab1
;;    :tropes [{:id "conflict" :label "Conflict" :archetypes ["hero" "beast"]}
;;             {:id "three-act-structure" :label "Three Act Structure" :archetypes ["chessmaster"]}
;;             {:id "the-reveal" :label "The Reveal" :archetypes ["hero" "magician"]}
;;             {:id "the-climax" :label "The Climax" :archetypes ["priest" "villain"]}
;;             {:id "the-end" :label "The End" :archetypes ["hero" "villain"]}
;;             {:id "chekhovs-gun" :label "Chekhov's Gun" :archetypes ["detective"]}]
;;    :archetypes [{:id "hero" :label "The Hero"}
;;                 {:id "villain" :label "The Villain"}
;;                 {:id "magician" :label "The Magician"}
;;                 {:id "priest" :label "The Priest"}
;;                 {:id "detective" :label "The Detective"}
;;                 {:id "beast" :label "The Beast"}
;;                 {:id "chessmaster" :label "The Chessmaster"}]
;;    :characters [{:id "luke-skywalker" :label "Luke Skywalker" :archetypes ["hero"]}
;;                 {:id "darth-vader" :label "Darth Vader" :archetypes ["villain"]}
;;                 {:id "tesla" :label "Nikola Tesla" :archetypes ["magician" "scientist"]}
;;                 {:id "hyde" :label "Mr Hyde" :archetypes ["beast" "scientist"]}
;;                 {:id "marlowe" :label "Philip Marlowe" :archetypes ["detective"]}
;;                 {:id "kasparov" :label "Gary Kasparov" :archetypes ["chessmaster"]}
;;                 {:id "baskerville" :label "William of Baskerville" :archetypes ["detective" "priest"]}]
;;    :our-tropes [nil]
;;    :timeline []
;;    :story ""})
