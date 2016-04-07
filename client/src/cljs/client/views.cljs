(ns client.views
    (:require [re-frame.core :as re-frame]
              [reagent.core :as reagent]
              [client.db :refer [default-db]]
              [re-com.core :as com]))

(def tab-list [{:id :tab1 :label "Tropes"}
               {:id :tab2 :label "Timeline"}
               {:id :tab3 :label "Story"}])

;; (def current-tab (reagent/atom (:id (first tab-list))))

(def spacer [com/gap :size "5px"])
(def gap [com/gap :size "15px"])

(defn change-trope [])

;; rewrite for nth and subscription
(defn trope-select [n]
  (let [our-tropes (re-frame/subscribe [:our-tropes])
        all-tropes (re-frame/subscribe [:tropes])]
    [com/v-box
     :children [
                [com/label :label "Trope Name"]
                spacer
                [com/single-dropdown
                 :width "300px"
                 :choices @all-tropes
                 :model (nth @our-tropes n)
                 :filter-box? true
                 :on-change #(re-frame/dispatch [:change-trope n %])]]]))

(defn char-select [role n]
  (let [
        chars (re-frame/subscribe [:chars-for-archetype role])
        our-tropes (re-frame/subscribe [:our-tropes])]
    [com/v-box
     :children [
                [com/label :label (clojure.string/capitalize role)]
                spacer
                [com/single-dropdown
                 :width "200px"
                 :choices @chars
                 ;; TODO: make random
                 :model (:id (first @chars))
                 :filter-box? true
                 :on-change #(change-trope)]]]))

(defn characters [n]
  (let [
        archetypes (re-frame/subscribe [:archetypes n])
        ;; our-tropes (re-frame/subscribe [:our-tropes])
        ;; archetypes (:archetypes (nth @our-tropes n))
        ]
    (if-not (or (empty? @archetypes) (nil? @archetypes))
      [com/v-box
       :style {:padding "20px" :background-color "#ddddff" :border "#9999ff solid 2px"}
       :children (into []
                           (apply concat (for [x @archetypes]
                                           [[char-select x n] spacer]))
                           )
       ])
     ))

(defn add-trope []
  [com/h-box
   :justify :center
   :children [
              [com/md-circle-icon-button
               :md-icon-name "zmdi-plus"
               :emphasise? true
               :on-click #(re-frame/dispatch [:add-trope])]]])


(defn subvert-trope [n]
  [com/h-box
   :justify :center
   :children [
              [com/button
               :class "btn-warning"
               :label "Subvert"
               :on-click #(re-frame/dispatch [:subvert-trope])]]]
  )

(defn remove-trope [n]
  [com/h-box
   :justify :center
   :children [
              [com/button
               :class "btn-danger"
               :label "Delete"
               :on-click #(re-frame/dispatch [:remove-trope n])]]]
  )

;; rewrite for nth and subscription
(defn trope-box [n]
              [com/v-box
               :style {:background-color "#ddffdd"
                       :border "2px solid #99ff99"}
               :padding "10px"
               :children [[trope-select n]
                          gap
                          [characters n]
                          gap
                          [com/h-box
                           :justify :center
                           :children [
                                      [subvert-trope]
                                      gap
                                      (if (> n 0)
                                        [remove-trope n])]]
                          ]
              ])

(defn trope-boxes []
  (let [our-tropes (re-frame/subscribe [:our-tropes])]
    [com/v-box
     :children (into [] (apply concat (for [t (range (count @our-tropes))] [[trope-box t] gap])))]))

(defn trope-content []
  [com/h-box
   :justify :center
   :children [
              [com/v-box
               :margin "50px"
               :width "500px"
               :children [
                          [trope-boxes]
                          [add-trope]]]]])

(defn timeline-content [])

(defn story-content [])

(defn tabs []
  (let [current-tab (re-frame/subscribe [:current-tab])]
    [com/horizontal-tabs
     :model @current-tab
     :tabs tab-list
     :on-change #(re-frame/dispatch [:tab-changed %])]))

(defn content []
  (let [current-tab (re-frame/subscribe [:current-tab])]
    (cond (= @current-tab :tab1) [trope-content]
          (= @current-tab :tab2) [timeline-content]
          (= @current-tab :tab3) [story-content])))

(defn title []
    (fn []
      [com/h-box
       :justify :center
       :children [
                  [com/title
                   :label "The OddJob Story Shaper"
                   :level :level1]
                  ]
       ]))

(defn main-panel []
  (fn []
    [com/v-box
     :height "100%"
     :children [[title]
                [tabs]
                [content]]]))
