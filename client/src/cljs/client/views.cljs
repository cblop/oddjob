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
                 :model (:id (nth @our-tropes n))
                 :filter-box? true
                 :on-change #(re-frame/dispatch [:change-trope n %])]]]))

(defn char-select [role chars sel n]
  (let [
        ;; chars (re-frame/subscribe [:chars-for-archetype role])
        ;; our-tropes (re-frame/subscribe [:our-tropes])
        ;; this-trope (nth @our-tropes n)
        ]
    [com/v-box
     :children [
                [com/label :label role :style {:font-size "smaller"}]
                spacer
                [com/single-dropdown
                 :width "200px"
                 :choices chars
                 ;; TODO: make random
                 :model (:id sel)
                 ;; :model nil
                 :filter-box? true
                 :on-change #(re-frame/dispatch [:change-char n % role])]]]))

(defn characters [n]
  (let [
        archetypes (re-frame/subscribe [:archetypes n])
        subverted (re-frame/subscribe [:subverted? n])
        all-chars (re-frame/subscribe [:chars-for-archetypes @archetypes])
        our-tropes (re-frame/subscribe [:our-tropes])
        sel-chars (:chars (nth @our-tropes n))
        ;; s-chars (if (nil? sel-chars) (take (count @archetypes) (repeat nil)) sel-chars)
        ;; chars (if @subverted (reverse @all-chars) @all-chars)
        ;; p (println chars)
        ;; p (println @archetypes)
        triples (map vector (set @archetypes) (set @all-chars) sel-chars)
        ;; our-tropes (re-frame/subscribe [:our-tropes])
        ;; archetypes (:archetypes (nth @our-tropes n))
        ]
    (if-not (or (empty? @archetypes) (nil? @archetypes))
      [com/v-box
       :style {:padding "20px" :background-color "#ddddff" :border "#9999ff solid 2px"}
       :children (concat [[com/label :label "Characters"] gap] (into []
                             (apply concat (for [[x y z] triples]
                                             [[char-select x y z n] spacer]))
                             ))
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


(defn story-dialog []
  (let [
        story (re-frame/subscribe [:story])]
    [com/v-box
     :children [
                [com/alert-box
                 :id 1
                 :alert-type :none
                 :heading "Story"
                 :body [:div (for [s @story] [:p s])]
                 :closeable? false
                 ]
                [com/h-box
                 :justify :center
                 :children [
                            [com/button
                             :label "OK"
                             :on-click #(re-frame/dispatch [:hide])]]]
                ]]
    ))

(defn generate-story []
  (let [show? (re-frame/subscribe [:show])]
    [com/h-box
     :justify :center
     :children [
                [com/button
                 :class "btn-success"
                 :label "Generate Story!"
                 :on-click #(re-frame/dispatch [:generate-story])]
                (when @show?
                  [com/modal-panel
                   :backdrop-color   "grey"
                   :backdrop-opacity 0.4
                   :wrap-nicely? true
                   :style {:font-family "Consolas"}
                   :child [story-dialog]
                   ]
                  )
                ]]))


(defn subvert-trope [n]
  (let [subverted (re-frame/subscribe [:subverted? n])]
    [com/h-box
     :justify :center
     :children [
                [com/button
                 :class "btn-warning"
                 :label (if @subverted "Un-subvert" "Subvert")
                 :on-click #(re-frame/dispatch [:subvert-trope n])]]])
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
  (let [
        subverted (re-frame/subscribe [:subverted? n])
        arches (re-frame/subscribe [:archetypes n])
        ]
    [com/v-box
     :style (if @subverted {:background-color "#ffdddd" :border "#ff9999 solid 2px"}
                {:background-color "#ddffdd"
                 :border "2px solid #99ff99"})
     :padding "10px"
     :children [[trope-select n]
                gap
                [characters n]
                gap
                [com/h-box
                 :justify :center
                 :children [
                            (if (> (count @arches) 1)
                              [subvert-trope n])
                            gap
                            (if (> n 0)
                              [remove-trope n])]]
                ]
     ]))

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
                          [add-trope]
                          gap
                          gap
                          gap
                          gap
                          [generate-story]]]]])

(defn timeline-content [])

(defn story-content [])

(defn tabs []
  (let [current-tab (re-frame/subscribe [:current-tab])]
    [com/horizontal-tabs
     :model @current-tab
     :tabs tab-list
     :on-change #(re-frame/dispatch [:tab-changed %])]))

;; (defn content []
;;   (let [current-tab (re-frame/subscribe [:current-tab])]
;;     (cond (= @current-tab :tab1) [trope-content]
;;           (= @current-tab :tab2) [timeline-content]
;;           (= @current-tab :tab3) [story-content])))

(defn content []
  [trope-content])

(defn title []
    (fn []
      [com/h-box
       :justify :center
       :children [
                  [com/title
                   :label "OddJob's Subversive Story Hat"
                   :level :level1]
                  ]
       ]))

(defn main-panel []
  (fn []
    [com/v-box
     :height "100%"
     :children [[title]
                [content]
                ]]))
