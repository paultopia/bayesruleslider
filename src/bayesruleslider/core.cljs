(ns bayesruleslider.core
    (:require
      [reagent.core :as r]))


;; -------------------------
;; Math

(defn posterior-b-given-a [a-given-b prior-b prior-a]
  (/ (* a-given-b prior-b) prior-a))

(defn total-probability-rule [prob-true
                              prob-positive-test-if-true
                              prob-positive-test-if-false]
  (let [prob-false (- 1 prob-true)]
    (+ (* prob-true prob-positive-test-if-true)
       (* prob-false prob-positive-test-if-false))))

(defn calculate-test [false-positive false-negative base-rate]
  (let [true-positive (- 1 false-negative)
        prior-positive (total-probability-rule base-rate
                                               true-positive
                                               false-positive)]
    (posterior-b-given-a true-positive base-rate prior-positive)
    ))

(defn round [value places]
  (let [multiplier (.pow js/Math 10 places)
        raised (* value multiplier)]
    (/(.round js/Math raised) multiplier)))

;; -------------------------
;; State

(def test-params (r/atom {:false-positive 0.05
                          :false-negative 0.05
                          :base-rate 0.02}))


(defn slider [param value]
  [:input {:type "range" :value value :min 0 :max 1 :step 0.01
           :style {:width "100%"}
           :on-change (fn [e]
                        (swap! test-params assoc param (.. e -target -value))
                        )}])

(defn calculator-component []
  (let [{:keys [false-positive false-negative base-rate]} @test-params
        posterior (calculate-test false-positive false-negative base-rate)]
    [:div
     [:div
      "False Positive Rate: " false-positive
      [slider :false-positive false-positive]]
     [:div
      "False Negative Rate: " false-negative
      [slider :false-negative false-negative]]
     [:div
      "Base Rate: " base-rate
      [slider :base-rate base-rate]]
     [:p "Probability of true condition given positive test result: " (round posterior 3)]
     ]))

;; -------------------------
;; Views

(defn home-page []
  [:div
   [:h2 "Bayes Rule Test Result Calculator"]
   [:p "To build a little bit more intuition for what's going on here, you can use the sliders below to calculate our posteriors on the test given specified error rates for false positives (what proportion of the time the test sees a sober person and says drunk), false negatives (what proprtion of the time the test sees a drunk person and says sober), and base rate (our prior). Note that the test defaults to the example we worked through above.  Change the parameters and see what happens!"]
   [calculator-component]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
