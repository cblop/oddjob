(ns client.views
    (:require [re-frame.core :as re-frame]
              [reagent.core :as reagent]
              [client.db :refer [default-db]]
              [re-com.core :as com]))

(def trope (atom ""))
(def tab-list [{:id :tab1 :label "Tropes"}
               {:id :tab2 :label "Timeline"}
               {:id :tab3 :label "Story"}])

;; (def current-tab (reagent/atom (:id (first tab-list))))

(def tropes (atom [trope]))

(def spacer [com/gap :size "5px"])
(def gap [com/gap :size "15px"])

(defn change-trope [])

(defn trope-select []
  (let [selected-trope (reagent/atom nil)
        all-tropes (re-frame/subscribe [:tropes])]
    [com/v-box
     :children [
                [com/label :label "Trope Name"]
                spacer
                [com/single-dropdown
                 :width "300px"
                 :choices @all-tropes
                 :model selected-trope
                 :filter-box? true
                 :on-change #(change-trope)]]]))

(defn add-trope []
  [com/h-box
   :justify :end
   :children [
              [com/md-circle-icon-button
               :md-icon-name "zmdi-plus"
               :on-click #()]]])

(defn trope-box []
  [com/v-box
   :style {:background-color "#f9ffe6"
           :border "2px solid #ecffb3"}
   :padding "10px"
   :children [[trope-select]
              ]])

(defn trope-boxes []
  [com/v-box
   :margin "50px"
   :width "400px"
   :children [[trope-box]
              gap
              [add-trope]]])

(defn trope-content []
  [trope-boxes])

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
