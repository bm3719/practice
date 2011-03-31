;;; Ch 16: CLOS: Generic functions

;; Generic functions: Defines an abstract operation, but doesn't provide the
;; implementation.
(defgeneric draw (shape)
  (:documentation "Draw the given shape on the screen."))

;; Methods: Implement the generic function.  This example requires a class
;; named circle.
;; (defmethod draw ((shape circle))
;;   ...
;;   )

(defgeneric withdraw (account amount)
  (:documentation "Withdraw the specified amount from the account.
Signal an error if the current balance is less than amount."))

;; (defmethod withdraw ((account bank-account) amount)
;;   (when (< (balance account) amount)
;;     (error "Account overdrawn."))
;;   (decf (balance account) amount))




