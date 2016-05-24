(ns cljs-boot-starter.client
  (:require [reagent.core :as ra :refer [atom render]]))

(enable-console-print!)

#_(defn hello []
  [:div
   "Hello world!"])

#_(defn sum []
(let [x (int (.-value (js/document.getElementById "num1")))
      y (int (.-value (js/document.getElementById "num2")))
      r (js/document.getElementById "res")
      op (.-value (js/document.getElementById "operator"))]
  (cond
   (= op "+") (set! (.-innerHTML r) (+ x y))
   (= op "-") (set! (.-innerHTML r) (- x y))
   (= op "*") (set! (.-innerHTML r) (* x y)))))
#_(defn testpwd []
  (let [epwd (.-value (js/document.getElementById "pwd"))
        repwd (.-value (js/document.getElementById "rpwd"))]
     (js/console.log (= epwd repwd))))

#_(defn hello []
  [:div
   [:span
    [:input {:type "text"
             :id "num1"
             :size 5}]
    [:input {:type "text"
             :id "num2"
             :size 5}]
    [:select {:id "operator"}
     [:option {:value "+"} "+"]
     [:option {:value "-"} "-"]
     [:option {:value "*"} "*"]]
    [:span "Result:"]
    [:span {:id "res"}]
    [:button {:id "Click"
              :on-click #(sum)}]]])
#_(defn hello []
  "password checking "
   [:div
    [:div
     [:span "Enter password"]
     [:input {:type "password"
              :id "pwd"}]]
    [:div 
     [:span "Re enter password"]
     [:input {:type "password"
            :id "rpwd"}]] 
    [:div
     [:button {:id "click"
               :value "submit"
               :on-click #(testpwd)}]]])

#_(defn simple-component [line]
  [:div
   [:p line]
   [:p
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])
#_(defn simple-parent []
   [:div 
    [:p "I include simple-component"]
    [simple-component "I am a component!"]])



#_(defn list-items []
  "Items in a sequence"
  [:div
    "List Items are"
   [:ul
   (for [i (range 3)]
      ^{:key i} [:li "Item " i])]])

#_(def clicks (atom 0))

#_(defn click-count []
  "managing state in Reagent"
  [:div
   "Click-count:" @clicks " "
   [:input {:type "button" 
            :value "Click"
           :on-click #(swap! clicks inc)}]])
   
;;Display time
#_(defn timer-component 
  "setTimeout every time the component is rendered to update a counter"
  []
  (let [timer (atom 0)]
    (fn []
      (js/setTimeout #(swap! timer inc) 1000)
      [:div 
        "Seconds Elapsed: " @timer])))
                   

#_(defn atom-changed 
  [value]
  [:input {:type "text"
            :value @value
            :on-change #(reset! value (-> % .-target .-value)) }])
          
#_(defn shared-atom 
  "share state management between components"
  []
  (let [val (atom "foo")]
    (fn []
     [:div
       [:p "The value is now: " @val]
       [:p "Change it here: " [atom-changed val]]])))




#_(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])


#_(defonce timer (atom (js/Date.)))

#_(defonce time-color (atom "#f34"))

#_(defonce time-updater (js/setInterval
                       #(reset! timer (js/Date.)) 1000))

#_(defn greeting [message]
  [:h1 message])

#_(defn clock []
  (let [time-str (-> @timer .toTimeString (clojure.string/split " ") first)]
    [:div.example-clock
     {:style {:color @time-color}}
     time-str]))

#_(defn color-input []
  [:div.color-input
   "Time color: "
   [:input {:type "text"
            :value @time-color
            :on-change #(reset! time-color (-> % .-target .-value))}]])

#_(defn simple-example 
  "Display time"
  []
  [:div
   [greeting "Hello world, it is now"]
   [clock]
   [color-input]])


;;BMI calculator

(def bmi-data (atom {:height 180 :weight 80}))

(defn calc-bmi []
  (let [{:keys [height weight bmi] :as data} @bmi-data
        h (/ height 100)]
    (if (nil? bmi)
      (assoc data :bmi (/ weight (* h h)))
      (assoc data :weight (* bmi h h)))))

(defn slider [param value min max]
  [:input {:type "range" 
           :value value 
           :min min 
           :max max
           :style {:width "100%"}
           :on-change (fn [e]
                        (swap! bmi-data assoc param (.-target.value e))
                        (when (not= param :bmi)
                          (swap! bmi-data assoc :bmi nil)))}])

(defn bmi-component []
  (let [{:keys [weight height bmi]} (calc-bmi)
        [color diagnose] (cond
                          (< bmi 18.5) ["orange" "underweight"]
                          (< bmi 25) ["inherit" "normal"]
                          (< bmi 30) ["orange" "overweight"]
                          :else ["red" "obese"])]
    [:div
     [:h3 "BMI calculator"]
     [:div
      "Height: " (int height) "cm"
      [slider :height height 100 220]]
     [:div
      "Weight: " (int weight) "kg"
      [slider :weight weight 30 150]]
     [:div
      "BMI: " (int bmi) " "
      [:span {:style {:color color}} diagnose]
      [slider :bmi bmi 10 50]]]))




#_(defn init []
  (ra/render-component [simple-component] (.-body js/document)))


(defn init []
  (render [bmi-component] (.getElementById js/document "my-app-area")))

(init)
