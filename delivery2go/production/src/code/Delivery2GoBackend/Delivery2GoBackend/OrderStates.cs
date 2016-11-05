using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Delivery2GoBackend
{
    public static class OrderStates
    {
        public static readonly int Open = 1;
        public static readonly int Submited = 2;
        public static readonly int Ready = 3;
        public static readonly int OnTheWay = 4;
        public static readonly int Delivered = 5;
        public static readonly int Cancelled = 6;
        public static readonly int Unavailable = 7;
        public static readonly int Arrived = 8;
    }
}