(ns client.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :tropes
 (fn [db _]
   (reaction (:tropes @db))))

(re-frame/register-sub
 :current-tab
 (fn [db _]
   (reaction (:current-tab @db))))
