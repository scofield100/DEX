using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using System.Web.Http.Routing;
using Delivery2GoBackend.Handlers;

namespace Delivery2GoBackend
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            //config.Routes.MapHttpRoute(
            //    name: "DefaultApi",
            //    routeTemplate: "api/{controller}/{id}",
            //    defaults: new { id = RouteParameter.Optional }
            //);

            config.Routes.MapHttpRoute(
              name: "DefaultApi",
              routeTemplate: "api/{controller}/{action}/{id}",
              defaults: new { id = RouteParameter.Optional, action = RouteParameter.Optional }            
            );

            config.MessageHandlers.Add(new SessionMessageHandler());

            //IHttpRoute route = config.Routes.CreateRoute(
            //                    routeTemplate: "api/{controller}/{action}/{id}",
            //                    defaults: new HttpRouteValueDictionary("route"),
            //                    constraints: null,
            //                    dataTokens: null,
            //                    handler: new SessionMessageHandler());
            //config.Routes.Add("DefaultApi", route);
        }
    }
}
