using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using Delivery2GoBackend.Models;

namespace Delivery2GoBackend.Handlers
{
    public class SessionMessageHandler : DelegatingHandler
    {
        public static string SECURITY_TOKEN = "D566E76F60C87C42B61DA1C4B8D984E7";
        

        protected override Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, CancellationToken cancellationToken)
        {                        
            if (!request.Headers.Contains("SessionId"))
            {
                return Task.FromResult(request.CreateResponse(System.Net.HttpStatusCode.Forbidden));
            }
            else
            {
                var sessionId = request.Headers.GetValues("SessionId").First();
                if (sessionId != SECURITY_TOKEN)
                {
                    return Task.FromResult(request.CreateResponse(System.Net.HttpStatusCode.Forbidden));
                }
            }

            return base.SendAsync(request, cancellationToken);
        }
    }
}