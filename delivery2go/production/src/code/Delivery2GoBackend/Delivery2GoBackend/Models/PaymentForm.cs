using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Delivery2GoBackend.Models
{
    public class PaymentForm
    {
        [Required(ErrorMessage = "Credit Card number required")]
        public string CredicCardNumber { get; set; }

        [Required(ErrorMessage = "Credic Card CVV number required")]
        public string CVV { get; set; }

        [Required]
        public DateTime ExpirationDate { get; set; }

        public string PostalCode { get; set; }
        
    }
}