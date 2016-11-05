using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Enterlib;
using Enterlib.Parsing;
using Enterlib.Validation;
using Delivery2GoBackend.Models;
using System.IO;
using System.Net.Http.Headers;
using System.Threading.Tasks;

namespace Delivery2GoBackend.Controllers
{
	public class ImageController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Image> queryBuider;	

        public ImageController()
            :this(new deliveryContext())
		{
			
		}

        public ImageController(deliveryContext context)
        {
            this.context = context;
            queryBuider = new QueryBuilder<Image>()
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
        }


		// GET api/image/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Images.AsNoTracking(), filter, orderby, skip, top, x => new
				{
					Id=x.Id,
					Location=x.Location,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
				}).ToList();
		}

		// GET api/image/get/5
		public object Get(int id)
		{
			return context.Images.AsNoTracking()
				.Where(x=>x.Id == id)
				.Select(x=> new
				{
					Id=x.Id,
					Location=x.Location,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/image/
		public HttpResponseMessage Post([FromBody]Image value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/image/
		public HttpResponseMessage Put([FromBody]Image value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (filter == null)
			{
				context.Entry(value).State = System.Data.EntityState.Modified;
			}
			else
			{
				var old = context.Images.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Location = value.Location;
				old.UpdateUserId = value.UpdateUserId;
				old.UpdateDate = value.UpdateDate;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/image/
		public bool Delete(string filter = null)
		{
			IQueryable<Image> query = context.Images;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Images.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/image/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Image> query = context.Images.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/image/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Images.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Id,
					Location=x.Location,
					UpdateUserId=x.UpdateUserId,
					UpdateUserName=x.UpdateUser.Name,
					UpdateDate=x.UpdateDate,
				})
			.FirstOrDefault();
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing)
				context.Dispose();
			base.Dispose(disposing);
		}


        [HttpGet]
        public HttpResponseMessage GetImage(int id, int width = 0, int height = 0)
        {
            Delivery2GoBackend.Models.Image image =null;
            using (deliveryContext context = new deliveryContext())
            {
                image = context.Images.Find(id);
            }

            if (image == null || image.ImageData == null)
                return Request.CreateResponse(HttpStatusCode.NotFound);

            HttpResponseMessage response;
            if (width == 0 && height == 0)
            {
                response = Request.CreateResponse(HttpStatusCode.OK);
                response.Content = new ByteArrayContent(image.ImageData);
                response.Content.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg");               
                return response;
            }

            response = Request.CreateResponse(HttpStatusCode.OK);
            MemoryStream stream = new MemoryStream(image.ImageData);
            System.Drawing.Bitmap original = new System.Drawing.Bitmap(stream);

            width = Math.Min(width, original.Width);

            //Mantener la proporcion de la imagen respecto  a la original
            height =Math.Min(height,  (original.Height * width) / original.Width);

            System.Drawing.Bitmap scaled = new System.Drawing.Bitmap(original, (int)width, (int)height);

            stream = new MemoryStream();
            scaled.Save(stream, System.Drawing.Imaging.ImageFormat.Jpeg);
            stream.Position = 0;

            response.Content = new StreamContent(stream);            
            response.Content.Headers.ContentType = new MediaTypeHeaderValue("image/jpeg");  
            
            return response;
        }

        [HttpPost]
        public async Task<bool> SaveImage(HttpRequestMessage request)
        {            
            byte[] data = await request.Content.ReadAsByteArrayAsync();

            var str = System.Text.Encoding.Default.GetString(data);

            var imageIdString = request.Headers.GetValues("imageId").FirstOrDefault();
            int imageId = int.Parse(imageIdString);


            using (deliveryContext context = new deliveryContext())
            {
                var image = context.Images.Find(imageId);
                image.ImageData = data;
                context.SaveChanges();
            }

            return true;            

        }


	}
}
